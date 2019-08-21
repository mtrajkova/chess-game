package com.project.chess.service.impl;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SsEmitter {

    private static Map<Long, SseEmitter> sseEmitterMap = Collections.synchronizedMap(new HashMap<>());

    public static Map<Long, SseEmitter> getSseEmitterMap() {
        return sseEmitterMap;
    }

    public static void setSseEmitterMap(Long id,SseEmitter sseEmitterMap) {
        SsEmitter.sseEmitterMap.put(id,sseEmitterMap);
    }
}
