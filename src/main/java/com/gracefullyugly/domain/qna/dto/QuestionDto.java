package com.gracefullyugly.domain.qna.dto;

import com.gracefullyugly.domain.qna.entity.QnA;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {

    @NotBlank(message = "질문은 필수입니다.")
    private String question;

    public QnA toEntity(Long userId, Long itemId) {

        return QnA.builder()
                .userId(userId)
                .itemId(itemId)
                .question(question)
                .build();

    }
}
