package com.fitness.aiservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//this service work is to process ai responses.
@Service
@Slf4j
@AllArgsConstructor
public class ActivityAIService {
    private final GeminiService geminiService;

    public Recommendation generateRecommendation(Activity activity){
        //8.create prompt
        String prompt= createPromptForActivity(activity);
        //9.calls Gemini API (External AI call)
        String aiResponse=geminiService.getRecommendations(prompt);
        log.info("RESPONSE FROM AI {}",aiResponse);
        //10.process response
        return processAIResponse(activity,aiResponse);

    }

    private Recommendation processAIResponse(Activity activity, String aiResponse) {
        try {
            //ObjectMapper is a class used in Java (from the Jackson library) to convert between Java objects and JSON data.
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode= mapper.readTree(aiResponse);
            JsonNode textNode=rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .get("parts")
                    .get(0)
                    .path("text");

            String jsonContent = textNode.asText()
                    .replace("```json", "")
                    .replace("```", "")
                    .replaceAll(",\\s*}", "}")
                    .trim();
           // log.info("RESPONSE FROM CLEANED AI {}",jsonContent);

            JsonNode analysisJson=mapper.readTree(jsonContent);
            JsonNode analysisNode=analysisJson.path("analysis");
            StringBuilder fullAnalysis=new StringBuilder();
            addAnalysisSection(fullAnalysis,analysisNode,"overall","Overall:");
            addAnalysisSection(fullAnalysis,analysisNode,"pace","Pace:");
            addAnalysisSection(fullAnalysis,analysisNode,"heartRate","Heart Rate:");
            addAnalysisSection(fullAnalysis,analysisNode,"caloriesBurned","Calories:");


            List<String> improvements= extractImprovements(analysisJson.path("improvements"));
            List<String> suggestions= extractSuggestions(analysisJson.path("suggestions"));
            List<String> safety= extractSafetyGuidelines(analysisJson.path("safety"));

            return Recommendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .type(activity.getType().toString())
                    .recommendation(fullAnalysis.toString().trim())
                    .improvements(improvements)
                    .suggestions(suggestions)
                    .safety(safety)
                    .createdAt(LocalDateTime.now())
                    .build();




        } catch (Exception e) {
        e.printStackTrace();
        return createDefaultRecommendation(activity);

        }
    }

    private Recommendation createDefaultRecommendation(Activity activity) {
        return Recommendation.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .type(activity.getType().toString())
                .recommendation("unable to generate detailed analysis")
                .improvements(Collections.singletonList("continue with your current routine"))
                .suggestions(Collections.singletonList("consider consulting a fitness consultant"))
                .safety(Arrays.asList(
                        "Always warm up before excercise",
                        "stay hydrated",
                        "listen to your body"
                ))
                .createdAt(LocalDateTime.now())
                .build();

    }

    private List<String> extractSafetyGuidelines(JsonNode safetyNode) {
        List<String> safety=new ArrayList<>();
        if(safetyNode.isArray()){
            safetyNode.forEach(item->safety.add(item.asText()));
        }
        return safety.isEmpty()?
                Collections.singletonList("Follow general safety guidelines"):
                safety;

    }

    private List<String> extractSuggestions(JsonNode suggestionsNode) {
    List<String> suggestions=new ArrayList<>();
    if(suggestionsNode.isArray()){
        suggestionsNode.forEach(suggestion->{
            String workout=suggestion.path("workout").asText();
            String description=suggestion.path("description").asText();
            suggestions.add(String.format("%s %s",workout,description));
        });
    }
    return suggestions.isEmpty()?
            Collections.singletonList("No specific suggestions provided"):
            suggestions;
    }

    private List<String> extractImprovements(JsonNode improvementsNode) {
List<String> improvements=new ArrayList<>();
if(improvementsNode.isArray()){
    improvementsNode.forEach(improvement->{
        String area=improvement.path("area").asText();
        String detail=improvement.path("recommendations").asText();
   improvements.add(String.format("%s %s", area,detail));

    });
}
return  improvements.isEmpty()?
        Collections.singletonList("No specific improvements provides"):
        improvements;
    }

    //"overall": "this was an excellent"
    //overall:this was an excellent.
    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
    if(!analysisNode.path(key).isMissingNode()){
        fullAnalysis.append(prefix)
                .append(analysisNode.path(key).asText())
                .append(("\n\n"));

    }

    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
                        Analyze this fitness activity and provide detailed recommendations in the JSON format:
                        {
                        "analysis":{
                        "overall": "overall analysis here",
                        "pace": "pace analysis here",
                        "heartRate": "heart rate analysis here",
                        "caloriesBurned": "calories analysis here"
                        },
                        "improvements":[
                        {
                        "area": "Area name",
                        "recommendations": "Detailed recommendation"
                        }
                        ],
                        "suggestions": [
                        {
                        "workout": "workout name",
                        "description": "Detailed workout description"
                        }
                        ],
                        "safety":[
                        "Safety point 1",
                        "Safety point 2"
                        ]
                        }
                        
                        Analyze this activity:
                        Activity Type: %s
                        Duration: %d minutes
                        Calories Burned: %d
                        Additional Metrics: %s
                        
                        provide detailed analysis focusing on performance,improvements,next workout suggestions, and safety guidelines.
                        Ensure the response follows the EXACT JSON format shown above.
                        """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics()
        );
    }
}