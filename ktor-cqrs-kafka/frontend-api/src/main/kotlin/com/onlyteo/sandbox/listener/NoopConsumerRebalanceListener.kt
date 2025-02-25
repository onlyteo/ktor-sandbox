package com.onlyteo.sandbox.listener

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.common.TopicPartition

class NoopConsumerRebalanceListener : ConsumerRebalanceListener {
    override fun onPartitionsRevoked(partition: MutableCollection<TopicPartition>?) {}
    override fun onPartitionsAssigned(partition: MutableCollection<TopicPartition>?) {}
}