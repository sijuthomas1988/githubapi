package com.skr.redcare.githubapi.service.impl;

import com.skr.redcare.githubapi.client.GithubAPIRetryableClient;
import com.skr.redcare.githubapi.exception.GithubAPIException;
import com.skr.redcare.githubapi.exception.ServiceException;
import com.skr.redcare.githubapi.model.GithubDataResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpServerErrorException;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class GithubDataServiceImplTest {

    @Mock
    private GithubAPIRetryableClient githubAPIRetryableClient;

    @InjectMocks
    private GithubDataServiceImpl githubDataService;

    @Test
    public void getRepositoryDetails_Success() throws GithubAPIException, ServiceException {
        Mockito.when(this.githubAPIRetryableClient.callGithubSearchRepo(any(), any(), any(), any(), any()))
                .thenReturn(new ResponseEntity<>(getResponseString_Success(), HttpStatus.OK));

        GithubDataResponse response = this.githubDataService.getRepositoryDetails("created:2019-12-10,language:java", "stars", "desc", 1, 1);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getData().size(), 1);
        Assert.assertEquals(response.getData().get(0).getLanguage(), "Java");
        Assert.assertEquals(response.getData().get(0).getIsPrivate(), false);
    }

    @Test(expected = GithubAPIException.class)
    public void getRepositoryDetails_ThrowsAPIException() throws GithubAPIException, ServiceException {
        Mockito.when(this.githubAPIRetryableClient.callGithubSearchRepo(any(), any(), any(), any(), any()))
                .thenThrow(HttpServerErrorException.ServiceUnavailable.class);
        GithubDataResponse response = this.githubDataService.getRepositoryDetails("created:2019-12-10,language:java", "stars", "desc", 1, 1);
        Assert.assertNull(response);
    }

    @Test(expected = ServiceException.class)
    public void getRepositoryDetails_ThrowsServiceException() throws GithubAPIException, ServiceException {
        Mockito.when(this.githubAPIRetryableClient.callGithubSearchRepo(any(), any(), any(), any(), any()))
                .thenReturn(new ResponseEntity<>("error string", HttpStatus.OK));
        GithubDataResponse response = this.githubDataService.getRepositoryDetails("created:2019-12-10,language:java", "stars", "desc", 1, 1);
        Assert.assertNull(response);
    }

    private String getResponseString_Success() {
        return "{\"total_count\":3906,\"incomplete_results\":false,\"items\":[{\"id\":174765647,\"node_id\":\"MDEwOlJlcG9zaXRvcnkxNzQ3NjU2NDc=\",\"name\":\"BigData-Notes\",\"full_name\":\"heibaiying/BigData-Notes\",\"private\":false,\"owner\":{\"login\":\"heibaiying\",\"id\":31504331,\"node_id\":\"MDQ6VXNlcjMxNTA0MzMx\",\"avatar_url\":\"https://avatars.githubusercontent.com/u/31504331?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/heibaiying\",\"html_url\":\"https://github.com/heibaiying\",\"followers_url\":\"https://api.github.com/users/heibaiying/followers\",\"following_url\":\"https://api.github.com/users/heibaiying/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/heibaiying/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/heibaiying/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/heibaiying/subscriptions\",\"organizations_url\":\"https://api.github.com/users/heibaiying/orgs\",\"repos_url\":\"https://api.github.com/users/heibaiying/repos\",\"events_url\":\"https://api.github.com/users/heibaiying/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/heibaiying/received_events\",\"type\":\"User\",\"site_admin\":false},\"html_url\":\"https://github.com/heibaiying/BigData-Notes\",\"description\":\"大数据入门指南:star:\",\"fork\":false,\"url\":\"https://api.github.com/repos/heibaiying/BigData-Notes\",\"forks_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/forks\",\"keys_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/keys{/key_id}\",\"collaborators_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/collaborators{/collaborator}\",\"teams_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/teams\",\"hooks_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/hooks\",\"issue_events_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/issues/events{/number}\",\"events_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/events\",\"assignees_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/assignees{/user}\",\"branches_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/branches{/branch}\",\"tags_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/tags\",\"blobs_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/git/blobs{/sha}\",\"git_tags_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/git/tags{/sha}\",\"git_refs_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/git/refs{/sha}\",\"trees_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/git/trees{/sha}\",\"statuses_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/statuses/{sha}\",\"languages_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/languages\",\"stargazers_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/stargazers\",\"contributors_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/contributors\",\"subscribers_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/subscribers\",\"subscription_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/subscription\",\"commits_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/commits{/sha}\",\"git_commits_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/git/commits{/sha}\",\"comments_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/comments{/number}\",\"issue_comment_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/issues/comments{/number}\",\"contents_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/contents/{+path}\",\"compare_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/compare/{base}...{head}\",\"merges_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/merges\",\"archive_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/{archive_format}{/ref}\",\"downloads_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/downloads\",\"issues_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/issues{/number}\",\"pulls_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/pulls{/number}\",\"milestones_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/milestones{/number}\",\"notifications_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/notifications{?since,all,participating}\",\"labels_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/labels{/name}\",\"releases_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/releases{/id}\",\"deployments_url\":\"https://api.github.com/repos/heibaiying/BigData-Notes/deployments\",\"created_at\":\"2019-03-10T01:40:01Z\",\"updated_at\":\"2024-01-25T09:46:05Z\",\"pushed_at\":\"2024-01-05T03:00:32Z\",\"git_url\":\"git://github.com/heibaiying/BigData-Notes.git\",\"ssh_url\":\"git@github.com:heibaiying/BigData-Notes.git\",\"clone_url\":\"https://github.com/heibaiying/BigData-Notes.git\",\"svn_url\":\"https://github.com/heibaiying/BigData-Notes\",\"homepage\":\"\",\"size\":24052,\"stargazers_count\":14949,\"watchers_count\":14949,\"language\":\"Java\",\"has_issues\":true,\"has_projects\":true,\"has_downloads\":true,\"has_wiki\":true,\"has_pages\":false,\"has_discussions\":false,\"forks_count\":4123,\"mirror_url\":null,\"archived\":false,\"disabled\":false,\"open_issues_count\":39,\"license\":null,\"allow_forking\":true,\"is_template\":false,\"web_commit_signoff_required\":false,\"topics\":[\"azkaban\",\"big-data\",\"bigdata\",\"flume\",\"hadoop\",\"hbase\",\"hdfs\",\"hive\",\"kafka\",\"mapreduce\",\"phoenix\",\"scala\",\"spark\",\"sqoop\",\"storm\",\"yarn\",\"zookeeper\"],\"visibility\":\"public\",\"forks\":4123,\"open_issues\":39,\"watchers\":14949,\"default_branch\":\"master\",\"score\":1.0}]}";
    }
}