// package com.ctrlaltdelinquents.backend.repo;

// import com.ctrlaltdelinquents.backend.model.ChatMessage;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import java.util.List;

// public interface ChatMessageRepo extends JpaRepository<ChatMessage, Integer>{

//     @Query("SELECT c FROM ChatMessage c WHERE c.chat.chatid = :chatid")
//     List<ChatMessage> findByChat(@Param("chatid") int chatid);
// }