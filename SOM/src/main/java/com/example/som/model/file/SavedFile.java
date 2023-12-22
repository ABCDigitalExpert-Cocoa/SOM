package com.example.som.model.file;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SavedFile {
    private Long file_id;
    private Long board_id;
    private Long diary_id;
    private Long hobby_id;
    private Long relief_id;
    private Long etc_id;
    private String original_filename;
    private String saved_filename;
    private Long file_size;

    public SavedFile(String original_filename, String saved_filename, Long file_size) {
        this.original_filename = original_filename;
        this.saved_filename = saved_filename;
        this.file_size = file_size;
    }
}
