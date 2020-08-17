package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("UpdateQuality")
class GildedRoseTest {

    @Test
    @DisplayName("Should Decrease Quality By And Sellin By 1 When the Item is normal and is not expired")
    void normalItemBeforeExpiry() {
        // Given
        String itemName = "+5 Dexterity Vest";
        final var item = new Item(itemName, 10, 20);
        Item[] items = new Item[]{ item };
        GildedRose app = new GildedRose(items);

        final var expectedQuality = 19;
        final var expectedSellIn = 9;

        // When
        app.updateQuality();

        // Then
        assertEquals(itemName, app.items[0].name);
        assertEquals(expectedQuality, app.items[0].quality);
        assertEquals(expectedSellIn, app.items[0].sellIn);
    }
}
