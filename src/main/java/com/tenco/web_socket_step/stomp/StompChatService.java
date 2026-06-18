package com.tenco.web_socket_step.stomp;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StompChatService {

    private final StompChatRepository  stompChatRepository;

    // 스프링에서 제공하는 메세지 전용 템플릿
    // 특정 주소 (/sub/...) 을 구독(subscribe)중인 client 에게 서버가 직접 메세지를 푸시 하게 도움
    private final SimpMessagingTemplate messagingTemplate;

    // 저장 --> 방송
    @Transactional
    public void saveAndBroadCast(Long roomId, String message, String sender) {
        StompChat stompChat = StompChat.builder()
                .roomId(roomId)
                .message(message)
                .sender(sender)
                .build();

        stompChatRepository.save(stompChat);

        // 2. 메시지 브로드 캐스트 (동적 주소 배달)
        // "홍길동" : "메세지"
        String formattedMessage = sender + ": " + message;
        // 예) roomId 5 라면 "/sub/chat/" 를 구독중인 클라이언트들에게 메세지가 초고속으로 전달됩니다.
        messagingTemplate.convertAndSend("/sub/chat/" + roomId, formattedMessage);
    }

    // 특정 채팅방의 과거 댜화 내역 전체 조회
    public List<StompChat> findMessageByRoomId(Long roomId) {
        return stompChatRepository.findByRoomId(roomId);
    }


}









