package com.onlyteo.sandbox.model

import com.expediagroup.graphql.generator.annotations.GraphQLSkipInputSuffix

@GraphQLSkipInputSuffix
data class Person(val name: String)
