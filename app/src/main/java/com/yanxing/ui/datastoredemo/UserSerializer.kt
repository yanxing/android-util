package com.yanxing.ui.datastoredemo

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object UserSerializer :Serializer<User> {

    override val defaultValue: User
        get() = User.getDefaultInstance()

    override fun readFrom(input: InputStream): User {
        try {
            return User.parseFrom(input)
        }catch (e: InvalidProtocolBufferException){
            throw CorruptionException("不能读取 proto",e)
        }
    }

    override fun writeTo(t: User, output: OutputStream) {
        t.writeTo(output)
    }

}
