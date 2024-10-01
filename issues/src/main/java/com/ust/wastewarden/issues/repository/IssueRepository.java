package com.ust.wastewarden.issues.repository;

import com.ust.wastewarden.issues.model.Issue;
import com.ust.wastewarden.issues.model.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByBinIdAndStatus(Long binId, IssueStatus status);

    List<Issue> findByStatus(IssueStatus issueStatus);

    List<Issue> findByUserId(Long userId);

}

