package com.ust.wastewarden.issues.controller;

import com.ust.wastewarden.issues.dtos.IssueDTO;
import com.ust.wastewarden.issues.model.Issue;
import com.ust.wastewarden.issues.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

//    @PostMapping("/report")
//    public ResponseEntity<Issue> reportIssue(
//            @RequestParam Long userId,
//            @RequestParam Long binId,
//            @RequestParam String description) {
//        Issue issue = issueService.reportIssue(userId, binId, description);
//        return ResponseEntity.ok(issue);
//    }

    // Create a new issue
    @PostMapping("/create")
    public ResponseEntity<IssueDTO> createIssue(@RequestParam Long userId, @RequestParam Long binId, @RequestParam String description) {
        IssueDTO newIssue = issueService.createIssue(userId, binId, description);
        return new ResponseEntity<>(newIssue, HttpStatus.CREATED);
    }

    // Get all pending issues (admin view)
    @GetMapping("/pending")
    public ResponseEntity<List<Issue>> getPendingIssues() {
        List<Issue> pendingIssues = issueService.getPendingIssues();
        return new ResponseEntity<>(pendingIssues, HttpStatus.OK);
    }

    // Resolve an issue
    @PutMapping("/resolve/{issueId}")
    public ResponseEntity<Issue> resolveIssue(@PathVariable Long issueId) {
        Issue resolvedIssue = issueService.resolveIssue(issueId);
        return new ResponseEntity<>(resolvedIssue, HttpStatus.OK);
    }

    // Get all issues raised by a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Issue>> getUserIssues(@PathVariable Long userId) {
        List<Issue> userIssues = issueService.getUserIssues(userId);
        return new ResponseEntity<>(userIssues, HttpStatus.OK);
    }
}
