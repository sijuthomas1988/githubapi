package com.skr.redcare.githubapi.controller;

import com.skr.redcare.githubapi.exception.GithubAPIException;
import com.skr.redcare.githubapi.exception.ServiceException;
import com.skr.redcare.githubapi.model.ErrorDetails;
import com.skr.redcare.githubapi.model.GithubDataResponse;
import com.skr.redcare.githubapi.service.GithubDataService;
import com.skr.redcare.githubapi.validation.OrderParamAssert;
import com.skr.redcare.githubapi.validation.QueryParamAssert;
import com.skr.redcare.githubapi.validation.SortParamAssert;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Slf4j
@OpenAPIDefinition
@Validated
public class GithubDataController {

    private GithubDataService githubDataService;

    public GithubDataController(GithubDataService githubDataService) {
        this.githubDataService = githubDataService;
    }

    @Operation(summary = "Search Github and return list of repositories with defined query passed as param.Accepted query" +
            "configs and multiple fields can be passed in the same query param delimited by a comma." +
            "An Example query param would be q=created:2019-01-10,language:java." +
            "Invalid values in the query param and non accepted fields results in error.",
            description = "Search Github and return list of repositories with defined query passed as param.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Github repositories fetched.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GithubDataResponse.class)))}),
            @ApiResponse(responseCode = "500", description = "An error occurred.", content =  {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            }),
            @ApiResponse(responseCode = "404", description = "Bad Request.", content =  {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            })
    })
    @RequestMapping(value = "/search/repositories", produces = {"application/json"}, method = RequestMethod.GET)
    @Timed
    public ResponseEntity<GithubDataResponse> getRepositoryDetails(@QueryParamAssert(value = "query") @RequestParam(name = "query") String query,
                                                                         @SortParamAssert (value = "sort") @RequestParam(name = "sort", defaultValue = "stars")  String sort,
                                                                         @OrderParamAssert (value = "order") @RequestParam(name = "order", defaultValue = "asc") String order,
                                                                         @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                         @RequestParam(name = "perPage", defaultValue = "1") Integer perPage) throws GithubAPIException, ServiceException {

        /**
         * The rationale behind using a GET API opposed to a POST witha request body of query params as individual fields is
         * due to future extensibilty as if there are more additional params to be added, then new fields needs to
         * be created, which requires code change. Here we have validations and accepted fields as configs.
         */
        // call service layer
        GithubDataResponse response = this.githubDataService.getRepositoryDetails(query, sort, order, page, perPage);
        log.info("Repository fetched sorted by " + sort + " in the " + order + " order");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
