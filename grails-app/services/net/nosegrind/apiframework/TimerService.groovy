package net.nosegrind.apiframework

/* ****************************************************************************
 * Copyright 2015 Owen Rubel
 *****************************************************************************/

import grails.converters.JSON
import grails.converters.XML
import grails.web.servlet.mvc.GrailsParameterMap

import org.grails.groovy.grails.commons.*

import javax.servlet.forward.*
import javax.servlet.http.HttpServletRequest
import net.nosegrind.apiframework.*
import org.apache.commons.collections.list.TreeList

/*
needs to be a treelist with
left being interceptor:before and
right being interceptor:after
 */
class TimerService {

	def apiResponseService

	List currentTimer = []

	void clearTimer() {
		currentTimer = []
	}

	LinkedHashMap getTimer(GrailsParameterMap params) {
		HttpServletRequest request = apiResponseService.getRequest()
		LinkedHashMap map = apiResponseService.formatList(currentTimer)
		return parseTracertResponse(request,params,map)
	}

	void startTime(String classname, String methodname) {
		String key = "${classname}/${methodname}".toString()
		Long time = System.currentTimeMillis()
		Map log = [:]
		log."${key}" = time
		currentTimer.add(log)
	}

	void endTime(String classname, String methodname) {
		String key = "${classname}/${methodname}"
		def lastIndex = currentTimer.findIndexOf { it.keySet()[0] == key }

		Long end = System.currentTimeMillis()

		//def lastIndex = currentTimer.indexOf(currentTimer.get(currentTimer.size()-1))
		LinkedHashMap newMap = currentTimer.get(lastIndex)

		if (newMap.keySet()[0] == key) {
			String index = newMap.keySet()[0]
			Long finalTime = newMap["${index}"]
			newMap["${key}"] = end - finalTime
			currentTimer.set(lastIndex, newMap)
		} else {
			println("#### FAIL - tried to end parent time in secondary loop ####")
		}
	}

	void endTime() {
		Long end = System.currentTimeMillis()

		def lastIndex = currentTimer.indexOf(currentTimer.get(currentTimer.size() - 1))
		LinkedHashMap newMap = currentTimer.get(lastIndex)

		String index = newMap.keySet()[0]
		Long finalTime = newMap["${index}"]
		newMap["${index}"] = end - finalTime
		currentTimer.set(lastIndex, newMap)
	}

	Map parseTracertResponse(HttpServletRequest request, GrailsParameterMap params, Map map){
		Map data = [:]
		switch(request.method) {
			case 'PURGE':
				// cleans cache; disabled for now
				break;
			case 'TRACE':
				break;
			case 'HEAD':
				break;
			case 'OPTIONS':
				String contentType = (params.contentType)?params.contentType:'application/json'
				String encoding = (params.encoding)?params.encoding:"UTF-8"
				LinkedHashMap doc = getApiDoc(params)
				data = ['content':doc,'contentType':contentType,'encoding':encoding]
				break;
			case 'GET':
				if(map?.isEmpty()==false){
					data = parseContentType(params, map)
				}
				break;
			case 'PUT':
				if(!map.isEmpty()){
					data = parseContentType(params, map)
				}
				break;
			case 'POST':
				if(!map.isEmpty()){
					data = parseContentType(params, map)
				}
				break;
			case 'DELETE':
				if(!map.isEmpty()){
					data = parseContentType(params, map)
				}
				break;
		}
		return ['apiToolkitContent':data.content,'apiToolkitType':data.contentType,'apiToolkitEncoding':data.encoding]
	}

	private Map parseContentType(GrailsParameterMap params, Map map){
		String content
		String contentType = "application/${params.format.toLowerCase()}"
		String encoding = (params.encoding)?params.encoding:"UTF-8"

		switch(params.format){
			case 'XML':
				content = map as XML
				break
			case 'JSON':
			default:
				content = map as JSON
		}

		return ['content':content,'type':contentType,'encoding':encoding]
	}

}