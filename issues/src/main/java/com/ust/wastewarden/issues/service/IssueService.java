package com.ust.wastewarden.issues.service;

import com.ust.wastewarden.issues.dtos.BinDTO;
import com.ust.wastewarden.issues.model.Issue;
import com.ust.wastewarden.issues.model.IssueStatus;
import com.ust.wastewarden.issues.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Autowired
    private RestTemplate restTemplate;

    // Raise a new issue
    public Issue reportIssue(Long userId, Long binId, String description) {
        // Check if the bin exists via Bin Service
        String binServiceUrl = "http://bin-service/bins/" + binId;
        ResponseEntity<BinDTO> response = restTemplate.getForEntity(binServiceUrl, BinDTO.class);
        if (!response.hasBody()) {
            throw new RuntimeException("Bin not found");
        }

        Issue issue = new Issue();
        issue.setUserId(userId);
        issue.setBinId(binId);
        issue.setDescription(description);
        issue.setStatus(IssueStatus.REPORTED);
        issue.setReportedAt(LocalDateTime.now());
        
        return issueRepository.save(issue);
    }

    // Resolve an issue
    // resolving work given to Admin to check the reported bin -> sets to "overflow" status
    // for bin by calling => updateBinStatus(Long id, int fillLevel=100)
    // and set the issue resolved (it by sending trucks to location of bin -> sets "empty" as bin status)
    public Issue resolveIssue(Long issueId) {
        Issue issue = issueRepository.findById(issueId).orElse(null);
//                .orElseThrow(() -> new ResourceNotFoundException("Issue not found"));
        issue.setStatus(IssueStatus.RESOLVED);
        issue.setResolvedAt(LocalDateTime.now());
        return issueRepository.save(issue);
    }
}
