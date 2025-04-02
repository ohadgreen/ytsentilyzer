package com.acme.aiassistant.services.comments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class WordCountServiceTest {

    @InjectMocks
    private WordCountService wordCountService;

    private Map<String, Integer> wordsCountMap;
    private TreeMap<Integer, List<String>> sortedCountsMap;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        // Initialize the service
        wordCountService = new WordCountService();

        // Simulate loading stop words manually
        WordCountService.STOP_WORDS_SET.add("the");
        WordCountService.STOP_WORDS_SET.add("is");

        wordsCountMap = new HashMap<>();
        sortedCountsMap = new TreeMap<>(Collections.reverseOrder());
    }

    @Test
    void testWordsCount_WithValidWords() {
        List<String> comments = Arrays.asList(
                "apple banana apple orange banana apple",
                "banana orange orange mango"
        );

        wordCountService.wordsCount(wordsCountMap, sortedCountsMap, comments);

        // Expected counts
        assertEquals(3, wordsCountMap.get("apple"));
        assertEquals(3, wordsCountMap.get("orange"));
        assertEquals(3, wordsCountMap.get("banana"));

        // Ensure sortedCountsMap is correct
        Set<String> words3FrequencySet = new HashSet<>(sortedCountsMap.get(3));
        assertEquals(3, words3FrequencySet.size());
        assertTrue(words3FrequencySet.contains("apple"));
        assertTrue(words3FrequencySet.contains("banana"));

        Set<String> words1FrequencySet = new HashSet<>(sortedCountsMap.get(1));
        assertEquals(1, words1FrequencySet.size());
        assertTrue(words1FrequencySet.contains("mango"));
    }

    @Test
    void testWordsCount_IgnoresStopWords() {
        List<String> comments = Arrays.asList("the apple is red", "apple is sweet");

        wordCountService.wordsCount(wordsCountMap, sortedCountsMap, comments);

        assertNull(wordsCountMap.get("the"));
        assertNull(wordsCountMap.get("is"));

        assertEquals(2, wordsCountMap.get("apple"));
    }

    @Test
    void testWordsCount_HandlesEmptyInput() {
        List<String> comments = Collections.emptyList();

        wordCountService.wordsCount(wordsCountMap, sortedCountsMap, comments);

        assertTrue(wordsCountMap.isEmpty());
        assertTrue(sortedCountsMap.isEmpty());
    }

    @Test
    void testWordsCount_HandlesCaseInsensitivity() {
        List<String> comments = List.of("Apple apple APPLE banana BANANA");

        wordCountService.wordsCount(wordsCountMap, sortedCountsMap, comments);

        assertEquals(3, wordsCountMap.get("apple"));
        assertEquals(2, wordsCountMap.get("banana"));

        assertEquals(List.of("apple"), sortedCountsMap.get(3));
        assertEquals(List.of("banana"), sortedCountsMap.get(2));
    }
}