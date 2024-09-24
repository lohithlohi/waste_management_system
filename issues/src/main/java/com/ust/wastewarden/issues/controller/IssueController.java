package com.ust.wastewarden.issues.controller;

import com.ust.wastewarden.issues.model.Issue;
import com.ust.wastewarden.issues.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping("/report")
    public ResponseEntity<Issue> reportIssue(
            @RequestParam Long userId,
            @RequestParam Long binId,
            @RequestParam String description) {
        Issue issue = issueService.reportIssue(userId, binId, description);
        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{issueId}/resolve")
    public ResponseEntity<Issue> resolveIssue(@PathVariable Long issueId) {
        Issue issue = issueService.resolveIssue(issueId);
        return ResponseEntity.ok(issue);
    }
}
