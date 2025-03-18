package com.onlyteo.sandbox.exception

import com.onlyteo.sandbox.errors.model.ProblemDetails

class ProblemDetailException(val problemDetails: ProblemDetails) : RuntimeException(problemDetails.detail)