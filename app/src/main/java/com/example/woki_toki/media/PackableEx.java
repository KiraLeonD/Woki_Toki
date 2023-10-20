package com.example.woki_toki.media;

public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}
