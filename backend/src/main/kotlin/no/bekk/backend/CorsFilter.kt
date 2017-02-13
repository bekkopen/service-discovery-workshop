package no.bekk.backend

import javax.servlet.*
import javax.servlet.http.HttpServletResponse

class CorsFilter : Filter {
    override fun destroy() {
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        if (response is HttpServletResponse) {
            response.addHeader("Access-Control-Allow-Origin", "*")
            response.addHeader("Access-Control-Allow-Headers", "Content-Type")
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE")
        }
        chain.doFilter(request, response)
    }

    override fun init(p0: FilterConfig?) {
    }
}