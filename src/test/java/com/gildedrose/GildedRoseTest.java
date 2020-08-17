package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("UpdateQuality")
class GildedRoseTest {

    public static final String DEXTERITY_VEST_NAME = "+5 Dexterity Vest";

    @Test
    @DisplayName("When the Item is normal and is not expired It should Decrease Quality By And Sellin By 1")
    void normalItemBeforeExpiry() {
        // Given
        final var itemName = "+5 Dexterity Vest";
        final var item = new Item(itemName, 10, 20);
        final var items = new Item[]{ item };
        final var app = new GildedRose(items);

        final var expectedQuality = 19;
        final var expectedSellIn = 9;

        // When
        app.updateQuality();

        // Then
        assertEquals(itemName, app.items[0].name);
        assertEquals(expectedQuality, app.items[0].quality);
        assertEquals(expectedSellIn, app.items[0].sellIn);
    }

    @Test
    @DisplayName("When the Item is normal and is expired It should Decrease Quality By 2 And Sellin By 1")
    void normalItemAfterExpiry() {
        // Given
        final var itemName = "+5 Dexterity Vest";
        final var item = new Item(itemName, 0, 20);
        final var items = new Item[]{ item };
        final var app = new GildedRose(items);

        final var expectedQuality = 18;
        final var expectedSellIn = -1;

        // When
        app.updateQuality();

        // Then
        assertEquals(itemName, app.items[0].name);
        assertEquals(expectedQuality, app.items[0].quality);
        assertEquals(expectedSellIn, app.items[0].sellIn);
    }

    @Test
    @DisplayName("When the Item has zero value It should Keep Quality at Zero")
    void zeroQualityItem() {
        // Given
        final var dexterityVestName = DEXTERITY_VEST_NAME;
        final var dexterityItem = new Item(dexterityVestName, 5, 0);
        final var elixirName = "Elixir of the Mongoose";
        final var elixirItem = new Item(elixirName, 0, 0);


        final var items = new Item[]{ dexterityItem, elixirItem };
        final var app = new GildedRose(items);

        final var expectedQuality = 0;

        // When
        app.updateQuality();

        // Then
        assertEquals(dexterityVestName, app.items[0].name);
        assertEquals(expectedQuality, app.items[0].quality);

        assertEquals(elixirName, app.items[1].name);
        assertEquals(expectedQuality, app.items[1].quality);
    }

    @Test
    @DisplayName("When the Item is Aged Brie It should Increase Quality")
    void agedBrieBeforeExpiry() {
        // Given
        final var itemName = "Aged Brie";
        final var unexpiredAgedBrie = new Item(itemName, 5, 10);
        final var expiredAgedBrie = new Item(itemName, 0, 10);
        final var items = new Item[]{ unexpiredAgedBrie, expiredAgedBrie };
        final var app = new GildedRose(items);

        final var expectedUnexpiredBrieQuality = 11;
        final var expectedExpiredBrieQuality = 12;

        // When
        app.updateQuality();

        // Then
        assertEquals(itemName, app.items[0].name);
        assertEquals(expectedUnexpiredBrieQuality, app.items[0].quality);

        assertEquals(itemName, app.items[1].name);
        assertEquals(expectedExpiredBrieQuality, app.items[1].quality);
    }
}
