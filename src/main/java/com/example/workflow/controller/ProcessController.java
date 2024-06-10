package com.example.workflow.controller;
import java.util.List;
import java.util.Map;


import com.example.workflow.model.Group;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class ProcessController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/app/{username}")
    public String index(@PathVariable("username") String username, Model model) {
        model.addAttribute("user", username);
        String url = "http://localhost:8080/engine-rest/identity/groups?userId=" + username;
        RestTemplate restTemplate = new RestTemplate();
        Map response = restTemplate.getForObject(url, Map.class);
        List<Map<String, String>> groups = (List<Map<String, String>>) response.get("groups");

        String viewName = determineView(groups);
        System.out.println(viewName);
        System.out.println("app");
        return viewName;
    }

    private String determineView(List<Map<String, String>> groups) {
        for (Map<String, String> group : groups) {
            if ("Dispeceri".equals(group.get("id"))) {
                return "dispeceriView";
            }
            else if ("Vozila".equals(group.get("id"))) {
                return "vozilaView";
            } else if ("ClanoviHS".equals(group.get("id"))) {
                return "clanoviHSView";
            }
        }
        return "defaultView";
    }


    @PostMapping("/start")
    public String startProcess(Model model) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Process_16ctghp");
        model.addAttribute("processInstanceId", processInstance.getId());
        return "redirect:/tasks/" + processInstance.getId();
    }

    @GetMapping("/tasks/{processInstanceId}")
    public String getTasks(@PathVariable("processInstanceId") String processInstanceId, Model model) {
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/showTask/{taskId}")
    public String showTask(@PathVariable("taskId") String taskId, Model model) {
        String templateName;
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        model.addAttribute("taskId", taskId);
        String taskName = task.getName();
        model.addAttribute("taskName", taskName);
        switch(taskName) {
            case "Dodaj adresu korisnika":
                templateName = "addAddress";
                break;

            case "Nova intervencija":
                templateName = "addIntervencija";
                break;

            case "Odaberi clana koji ce popunit izvjesce":
                templateName = "selectClan";
                break;

            default:
                templateName = "defaultTemplate";
                break;
        }
        return templateName;
    }

}

