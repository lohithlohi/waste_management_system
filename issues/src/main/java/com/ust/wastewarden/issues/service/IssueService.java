package com.ust.wastewarden.issues.service;

import com.ust.wastewarden.issues.FeignClients.BinFeignClient;
import com.ust.wastewarden.issues.FeignClients.UserFeignClient;
import com.ust.wastewarden.issues.dtos.BinDTO;
import com.ust.wastewarden.issues.dtos.IssueDTO;
import com.ust.wastewarden.issues.dtos.UserDTO;
import com.ust.wastewarden.issues.model.Issue;
import com.ust.wastewarden.issues.model.IssueStatus;
import com.ust.wastewarden.issues.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserFeignClient userFeignClient;
    private final BinFeignClient binFeignClient;
    public IssueService(IssueRepository issueRepository, UserFeignClient userFeignClient, BinFeignClient binFeignClient) {
        this.issueRepository = issueRepository;
        this.userFeignClient = userFeignClient;
        this.binFeignClient = binFeignClient;
    }

    public IssueDTO createIssue(Long userId, Long binId, String description) {
        // Fetch User and Bin details via Feign
        UserDTO user = userFeignClient.getUserById(userId);
        BinDTO bin = binFeignClient.getBinById(binId);

        // Create and save the Issue
        Issue issue = new Issue();
        issue.setDescription(description);
        issue.setUserId(user.id());
        issue.setBinId(bin.id());
        issue.setStatus(IssueStatus.PENDING);
        issue.setCreatedAt(LocalDateTime.now());

        Issue savedIssue = issueRepository.save(issue);

        // Convert the Issue entity to IssueDTO and return
        return new IssueDTO(savedIssue.getId(), description, user, bin, savedIssue.getStatus(), savedIssue.getCreatedAt(), savedIssue.getResolvedAt());
    }


    // Resolve an issue
    // resolving work given to Admin to check the reported bin -> sets to "overflow" status
    // for bin by calling => updateBinStatus(Long id, int fillLevel=100)
    // and set the issue resolved (it by sending trucks to location of bin -> sets "empty" as bin status)
    public Issue resolveIssue(Long issueId) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));
        issue.setStatus(IssueStatus.RESOLVED);
        issue.setResolvedAt(LocalDateTime.now());
        return issueRepository.save(issue);
    }


    public List<Issue> getPendingIssues() {
        return issueRepository.findByStatus(IssueStatus.PENDING);
    }

    public List<Issue> getUserIssues(Long userId) {
        return issueRepository.findByUserId(userId);
    }

}
