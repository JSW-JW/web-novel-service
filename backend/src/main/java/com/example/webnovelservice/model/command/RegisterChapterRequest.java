package com.example.webnovelservice.model.command;


public record RegisterChapterRequest(Long novelId, String title, String contents) {
}
