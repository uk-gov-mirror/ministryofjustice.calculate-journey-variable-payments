package uk.gov.justice.digital.hmpps.pecs.jpc.config

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView
import uk.gov.justice.digital.hmpps.pecs.jpc.service.MonitoringService
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ExceptionHandler(private val monitoringService: MonitoringService) {

  private val logger = LoggerFactory.getLogger(javaClass)

  @ExceptionHandler(AccessDeniedException::class)
  fun handleAccessDeniedException(e: AccessDeniedException, request: HttpServletRequest): ModelAndView {
    logger.warn("Access denied exception", e)

    return ModelAndView()
      .apply {
        this.viewName = "error/403"
        this.status = HttpStatus.FORBIDDEN
      }.also {
        SecurityContextHolder.getContext().authentication?.let {
          logger.warn("User: ${it.name} attempted to access the protected URL: ${request.requestURI}")
        }
      }
  }

  @ExceptionHandler(java.lang.Exception::class)
  fun handleException(e: java.lang.Exception): ModelAndView {
    logger.error("Unexpected exception", e)

    return ModelAndView()
      .apply {
        this.viewName = "error"
        this.status = HttpStatus.BAD_REQUEST
      }
      .also { monitoringService.capture("An unexpected error has occurred in the JPC application, see the logs for more details.") }
  }
}
