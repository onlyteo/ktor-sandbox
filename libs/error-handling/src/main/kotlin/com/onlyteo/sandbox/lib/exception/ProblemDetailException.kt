package com.onlyteo.sandbox.exception

import com.onlyteo.sandbox.lib.errors.model.ProblemDetails

class ProblemDetailException(val problemDetails: ProblemDetails) : RuntimeException(problemDetails.detail)