package com.example.talky.domain.practice.service;

import com.example.talky.domain.practice.repository.AnswerRepository;
import com.example.talky.domain.practice.repository.QuestionRepository;
import com.example.talky.domain.practice.web.dto.GetAllRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PracticeServiceImpl implements PracticeService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    // pracId를 이용한 상황별 질문 및 답변 조회 로직. nextQuestionId를 던져서, 프론트엔드로부터 다음 질문을 연결하게끔 할 예정
    @Override
    public GetAllRes getPractice(Long pracId) {
         return new GetAllRes(
                questionRepository.findAllByPracId(pracId)
                        .stream()
                        .map(q -> new GetAllRes.QuestionSet(
                                q.getId(),
                                q.getSentence(),
                                q.getAnswers().stream()
                                        .map(a -> new GetAllRes.QuestionSet.AnswerSet(
                                                a.getAnswer(),
                                                a.getNextQuestionId()
                                        ))
                                        .collect(Collectors.toList())
                        ))
                        .collect(Collectors.toList())

//                answerRepository.findAllByQuestionId(pracId)
//                        .stream()
//                        .map(a -> new GetAllRes.AnswerSet(
//                                a.getAnswer(),
//                                a.getNextQuestionId()))
//                        .collect(Collectors.toList())
         );
    }
}
