# Vert.x 3 REST framework

## Introduction
This framework aims to ease the adoption of Vert.x 3 by people used to using frameworks like Jersey and RESTEasy. It introduces the following concepts into Vert.x 3:

- Content negotiation (`@Consumes` and `@Produces`)
- Pre-matching request filters
- Request filters and labelled request filters (`@NameBinding`)
- Response filters and labelled response filters (`@NameBinding`)

#### Work in Progress
Current work includes implementing the concept of a SecurityContext and Principal to be used for security.

#### Current limitations
Currently the `Accept` header matching for content negotiation is still a bit primitive. The framework should work perfectly for 99% of use cases when building strict RESTful  services though. At present you can only register a single consumption content type per resource, and the framework will only use exact matching instead of a quality score best fit approach.

#### About Request and Response filter priority
To match RESTEasy and Jersey does priorities, please note that the order of execution is as follows; Request filters are executed in ascending priority, i.e. from 1 to *max*. Response filters however are executed in reverse order, i.e. from *max* to 0. The priority number 0 for request filters are reserved for pre-matching filters.
<p>
In the case where priorities are equal, filters are chosen arbitrarily, so please keep in this consideration if you filter chains are order dependant.
<p>
The default values when creating filters without priorities will always cause them to be executed in the least important position. Request filters are created with priority `Integer.MAX_VALUE`, so are executed last, while response filters are also created with `Integer.MAX_VALUE`, causing them to execute first (i.e. that prioritised response filters have the last say).


## Usage

### Registering Resources

#### Create a `RestResourceMatcher` instance
    final RestRouteMatcher rrm = RestRouteMathcer.restRouteMathcer();


#### Register a resource (`GET` with default options)
    rrm.resource("/some/path", HttpMethod.GET, (request) -> {});
    
or, if you want to use explicit methods:

    rrm.resource("/some/path", HttpMethod.GET, this::handleSomePath);
    
    private void handleSomePath(final RestRequest request) {
        // handle the request
    }
    

#### Register a resource (`POST` consuming `application/json` with parameter `v=0.5` and produces `application/xml`)
    rrm.resource(
        "/some/path",
        HttpMethod.POST,
        new ResourceOptions()
                .consumes("application/json; v=0.5")
                .produces("application/xml")
        (request) -> {});
    
#### Register a resource with filter name binding
    rrm.resource(
            "/some/path",
            HttpMethod.GET,
            new ResourceOptions()
                    .requestFilters("some-filter")
            (request) -> {});
        

### Registering Filters

#### Pre-matching Request Filter
    rrm.requestFilter(
             new RequestFilterOptions().type(RequestFilterType.PRE_MATCH),
             (context) -> {});
             
or, if you want to use explicit methods:

    rrm.requestFilter(
             new RequestFilterOptions().type(RequestFilterType.PRE_MATCH),
             (context) -> this::filterSomething);
             
    private void filterSomething(final RestRequestContext context) {
        // handle filter
    }
    
#### Request Filter
    rrm.requestFilter(
             new RequestFilterOptions().type(RequestFilterType.REQUEST),
             (context) -> {});
             
#### Request Filter with `@NameBinding`
    rrm.requestFilter(
             new RequestFilterOptions()
                     .type(RequestFilterType.REQUEST)
                     .label("my-bound-filter"),
             (context) -> {});
             
#### Response Filter
    rrm.responseFilter((context) -> {});
    
#### Response Filter with priority and `@NameBinding`
    rrm.responseFilter(
            new ResponseFilterOptions()
                    .priority(1)
                    .label("my-bound-response-filter"),
            (context) -> {});