
![alt text](https://github.com/orubel/logos/blob/master/beapi_logo_large.png)
# BeApi(tm) Api Framework ( https://www.beapi.io/ )
## Recent Stable Release : [0.9.7](http://dl.bintray.com/orubel/plugins/org/grails/plugins/api-framework/)
## Documentation/Installation - https://www.beapi.io/documentation

### Backend Implementation - https://github.com/orubel/beapi_backend
### Frontend Implementation - https://github.com/orubel/beapi_frontend

### Old Documentation - http://orubel.github.io/Beapi-API-Framework/

***
### Description
The BeAPI Framework is a full featured reactive api framework providing automation and simplification of api's for scale. Some features include:

- **Automated Batching:** all endpoints are batchable by default with AUTH ROLES assignable to restrict access. Batching can also be TOGGLED to turn this feature ON/OFF per endpoint.

- **Built-in CORS:** Cross Origin Request handling secures all endpoints from domains that you don't want.

- **JWT Tokens:** JWT Token handling for Javascript frontends to allow or better abstraction of the VIEW layer

- **Web Hooks:** Enables secured Web Hooks for any endpoint so your developers/users can get push notification on updates.

- **Throttling & Rate/Data Limits:** Data Limits/Rate limits and Throttling for all API's through easy to configure options

- **Autogenerated APIDocs:**  Unlike Swagger/OpenApi which shows the ApiDocs regardless of ROLE, APIDocs are autogenerated based on your ROLE thus showing only the endpoints that you have access to.

- **Shared I/O state:** Also unlike Swagger/OpenApi, the data associated with functionality for REQUEST/RESPONSE does not exist in TWO PLACES and thus can be synchronized making it moire secure, stable, faster and flexible. You can make changes to your apis, security and definitions on the fly all without taking your servers down.

- **API Chaining(tm):** rather than using HATEOASto make a request, get a link, make a request, get a link, make a request, etc... api chaining allows for creation of an 'api monad' wherein the output from a related set of apis can be chained allowing the output from one api to be accepted as the input to the next and so on and be passed with ONE REQUEST AND ONE RESPONSE.

- **Localized API Cache:** returned resources are cached,stored and updated with requesting ROLE/AUTH. Domains extend a base class that auto update this cache upon create/update/delete. This speeds up your api REQUEST/RESPONSE x10

- **Built-In Profiler:** Profile your API's and have them deliver a metrics report of time it takes for every class/method to deliver so you can optimize queries, methods services

***

### FAQ

**Q: How hard is this to implement?**
**A:** BeApi is 'Plug-N-Play'. Merely install the plugin and it takes care of the 'REST'. The only thing you have to do is build an IO state file for each controller or use the built in bootstrap command:
```
./gradlew GenerateIostate
```
This enables us to separate all IO data from functionality so it can be shared with other services in the architecture.

**Q: How do I implement the listener for IO state webhook on my proxy/Message queue?**
**A:** It merely requires an endpoint to send the data to. As a side project, I may actually supply a simple daemon in the future with ehCache to do this for people.

