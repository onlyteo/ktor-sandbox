package com.onlyteo.sandbox.exception

import com.onlyteo.sandbox.model.ProblemDetails

class ProblemDetailException(private val problemDetails: ProblemDetails) : RuntimeException(problemDetails.detail)