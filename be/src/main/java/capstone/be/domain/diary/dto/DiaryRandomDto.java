package capstone.be.domain.diary.dto;

import capstone.be.domain.diary.domain.BProperties;
import capstone.be.domain.diary.domain.Diary;
import capstone.be.domain.hashtag.dto.HashtagDto;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiaryRandomDto {
    private String title;
    private String mood;
    private String date;
    private String weather;
    private String content;

    public static DiaryRandomDto from(Diary diary){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = diary.getCreatedAt().format(formatter);

        return new DiaryRandomDto(diary.getTitle(),
                diary.getMood(),
                formattedDate,
                diary.getWeather(),
                diary.getBlocks().stream().filter(x -> x.getType().equals("text")).findFirst().get().getData().getText());
    }


}