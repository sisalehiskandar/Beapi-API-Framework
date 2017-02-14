package net.nosegrind.apiframework

import grails.transaction.Transactional
import grails.util.Environment

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import groovy.json.JsonSlurper
import groovy.util.XmlSlurper

import java.util.Enumeration

@Transactional
class CorsService {
    def grailsApplication

    boolean processPreflight(HttpServletRequest request, HttpServletResponse response) {
        Map corsInterceptorConfig = (Map) grailsApplication.config.corsInterceptor

        String[] includeEnvironments = corsInterceptorConfig['includeEnvironments']?: null
        String[] excludeEnvironments = corsInterceptorConfig['excludeEnvironments']?: null
        String[] allowedOrigins = corsInterceptorConfig['allowedOrigins']?: null

        if( excludeEnvironments && excludeEnvironments.contains(Environment.current.name) )  { // current env is excluded
            // skip
            false
        }
        else if( includeEnvironments && !includeEnvironments.contains(Environment.current.name) )  {  // current env is not included
            // skip
            false
        }

        String origin = request.getHeader("Origin");
        boolean options = ("OPTIONS" == request.method)
        if (options) {
            response.setHeader("Allow", "GET, HEAD, POST, PUT, DELETE, TRACE, PATCH, OPTIONS")
            if (origin != null) {
                response.setHeader("Access-Control-Allow-Headers", "Cache-Control, Pragma, WWW-Authenticate, Origin, Authorization, Content-Type, XMLHttpRequest, X-Requested-With, Access-Control-Request-Method, Access-control-Request-Headers")
                //response.setHeader("Access-Control-Allow-Headers","*")
                response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, PATCH, OPTIONS")
                response.setHeader("Access-Control-Allow-Credentials","true")
                response.setHeader("Access-Control-Max-Age", "3600")
                response.setHeader("Access-Control-Expose-Headers","*")
                request.getHeader('Access-Control-Request-Headers')
            }
        }


        if(allowedOrigins && allowedOrigins.contains(origin)) { // request origin is on the white list
            println("### ... allowed origins")
            // add CORS access control headers for the given origin
            response.setHeader("Access-Control-Allow-Origin", origin)
            response.setHeader("Access-Control-Allow-Credentials", "true")
            response.writer.flush()
            return false
        } else if( !allowedOrigins ) { // no origin white list
            // add CORS access control headers for all origins
            println("### ... allowing this origin")
            response.setHeader("Access-Control-Allow-Origin", origin ?: "*")
            response.setHeader("Access-Control-Allow-Credentials", "true")
            response.writer.flush()
            return false
        }

        //options
    }
}