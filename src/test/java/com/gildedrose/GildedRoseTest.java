package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("UpdateQuality")
class GildedRoseTest {

    public static final String DEXTERITY_VEST_NAME = "+5 Dexterity Vest";
    public static final String AGED_BRIE = "Aged Brie";

    @Test
    @DisplayName("When the Item is normal and is not expired It should Decrease Quality By And Sellin By 1")
    void normalItemBeforeExpiry() {
        // Given
        final var itemName = GildedRoseTest.DEXTERITY_VEST_NAME;
        final var item = new Item(itemName, 10, 20);
        final var items = new Item[]{item};
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
        final var itemName = GildedRoseTest.DEXTERITY_VEST_NAME;
        final var item = new Item(itemName, 0, 20);
        final var items = new Item[]{item};
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


        final var items = new Item[]{dexterityItem, elixirItem};
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
        final var itemName = AGED_BRIE;
        final var unexpiredAgedBrie = new Item(itemName, 5, 10);
        final var expiredAgedBrie = new Item(itemName, 0, 10);
        final var items = new Item[]{unexpiredAgedBrie, expiredAgedBrie};
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

    @Test
    @DisplayName("When the Brie has a quality of 50 It should not increase in Quality")
    void agedBrieWithQualityOf50() {
        // Given
        final var itemName = AGED_BRIE;
        final var unexpiredAgedBrie = new Item(itemName, 5, 50);
        final var expiredAgedBrie = new Item(itemName, 0, 50);
        final var items = new Item[]{unexpiredAgedBrie, expiredAgedBrie};
        final var app = new GildedRose(items);

        final var expectedUnexpiredBrieQuality = 50;
        final var expectedExpiredBrieQuality = 50;

        // When
        app.updateQuality();

        // Then
        assertEquals(itemName, app.items[0].name);
        assertEquals(expectedUnexpiredBrieQuality, app.items[0].quality);

        assertEquals(itemName, app.items[1].name);
        assertEquals(expectedExpiredBrieQuality, app.items[1].quality);
    }

    @Test
    @DisplayName("When An Item is a Sulfaras It should not decrease in Quality nor SellIn days")
    void sulfaras() {
        // Given
        final var sulfarasRagnarosName = "Sulfuras, Hand of Ragnaros";

        final var unexpiredSulfaras = new Item(sulfarasRagnarosName, 56, 80);
        final var justExpiredRaganaros = new Item(sulfarasRagnarosName, 0, 80);
        final var pastSellinRegagnaros = new Item(sulfarasRagnarosName, -1, 80);

        final var items = new Item[]{unexpiredSulfaras, justExpiredRaganaros, pastSellinRegagnaros};
        final var app = new GildedRose(items);

        final var expectedQuality = 80;

        // When
        app.updateQuality();

        // Then
        assertEquals(sulfarasRagnarosName, app.items[0].name);
        assertEquals(unexpiredSulfaras.sellIn, app.items[0].sellIn);
        assertEquals(expectedQuality, app.items[0].quality);

        assertEquals(sulfarasRagnarosName, app.items[1].name);
        assertEquals(justExpiredRaganaros.sellIn, app.items[1].sellIn);
        assertEquals(expectedQuality, app.items[1].quality);

        assertEquals(sulfarasRagnarosName, app.items[2].name);
        assertEquals(pastSellinRegagnaros.sellIn, app.items[2].sellIn);
        assertEquals(expectedQuality, app.items[2].quality);
    }

    @Test
    @DisplayName("When a backstage pass expires in more than 10 days It should increase in Quality by 1")
    void backstageExpiresInMoreThan10Days() {
        // Given
        final var backStageName = "Backstage passes to a TAFKAL80ETC concert";

        final var backstage11Days = new Item(backStageName, 11, 20);
        final var backStage20Days = new Item(backStageName, 20, 20);

        final var items = new Item[]{backstage11Days, backStage20Days};
        final var app = new GildedRose(items);

        final var expectedQuality = 21;
        final var expectedSellInBackstage11Days = 10;
        final var expectedSellInBackStage20Days = 19;

        // When
        app.updateQuality();

        // Then
        assertEquals(backStageName, app.items[0].name);
        assertEquals(expectedSellInBackstage11Days, app.items[0].sellIn);
        assertEquals(expectedQuality, app.items[0].quality);

        assertEquals(backStageName, app.items[1].name);
        assertEquals(expectedSellInBackStage20Days, app.items[1].sellIn);
        assertEquals(expectedQuality, app.items[1].quality);
    }

    @Test
    @DisplayName("When a backstage pass expires in less than 10 days It should increase in Quality by 2")
    void backstageExpiresBetween10And5Days() {
        // Given
        final var backStageName = "Backstage passes to a TAFKAL80ETC concert";

        final var backstage10Days = new Item(backStageName, 10, 41);
        final var backStage6Days = new Item(backStageName, 6, 41);

        final var items = new Item[]{backstage10Days, backStage6Days};
        final var app = new GildedRose(items);

        final var expectedQuality = 43;
        final var expectedSellInBackstage10Days = 9;
        final var expectedSellInBackStage6Days = 5;

        // When
        app.updateQuality();

        // Then
        assertEquals(backStageName, app.items[0].name);
        assertEquals(expectedSellInBackstage10Days, app.items[0].sellIn);
        assertEquals(expectedQuality, app.items[0].quality);

        assertEquals(backStageName, app.items[1].name);
        assertEquals(expectedSellInBackStage6Days, app.items[1].sellIn);
        assertEquals(expectedQuality, app.items[1].quality);
    }

    @Test
    @DisplayName("When a backstage pass expires in less than 10 days It should increase in Quality by 2")
    void backstageExpiresInLess5Days() {
        // Given
        final var backStageName = "Backstage passes to a TAFKAL80ETC concert";

        final var backstage5Days = new Item(backStageName, 5, 15);
        final var backStage3Days = new Item(backStageName, 3, 15);

        final var items = new Item[]{backstage5Days, backStage3Days};
        final var app = new GildedRose(items);

        final var expectedQuality = 18;
        final var expectedSellInBackstage5Days = 4;
        final var expectedSellInBackStage3Days = 2;

        // When
        app.updateQuality();

        // Then
        assertEquals(backStageName, app.items[0].name);
        assertEquals(expectedSellInBackstage5Days, app.items[0].sellIn);
        assertEquals(expectedQuality, app.items[0].quality);

        assertEquals(backStageName, app.items[1].name);
        assertEquals(expectedSellInBackStage3Days, app.items[1].sellIn);
        assertEquals(expectedQuality, app.items[1].quality);
    }

    @Test
    @DisplayName("When a backstage has a selling of 0 or less days It should have a quality of zero")
    void expiredBackstages() {
        // Given
        final var backStageName = "Backstage passes to a TAFKAL80ETC concert";

        final var backstage0Days = new Item(backStageName, 0, 15);
        final var backStageExpiredFo1Day = new Item(backStageName, -1, 15);

        final var items = new Item[]{backstage0Days, backStageExpiredFo1Day};
        final var app = new GildedRose(items);

        final var expectedQuality = 0;
        final var expectedSellInBackstage5Days = -1;
        final var expectedSellInBackStage3Days = -2;

        // When
        app.updateQuality();

        // Then
        assertEquals(backStageName, app.items[0].name);
        assertEquals(expectedSellInBackstage5Days, app.items[0].sellIn);
        assertEquals(expectedQuality, app.items[0].quality);

        assertEquals(backStageName, app.items[1].name);
        assertEquals(expectedSellInBackStage3Days, app.items[1].sellIn);
        assertEquals(expectedQuality, app.items[1].quality);
    }

    @Test
    @DisplayName("A backstage should quality can't exceed 50")
    void backstageQualityCantExceedFifty() {
        // Given
        final var backStageName = "Backstage passes to a TAFKAL80ETC concert";

        final var backstageInMoreThan10Days = new Item(backStageName, 12, 50);
        final var backstageInLessThan10Days = new Item(backStageName, 9, 49);
        final var backstageInLessThan5Days = new Item(backStageName, 3, 48);

        final var items = new Item[]{backstageInMoreThan10Days, backstageInLessThan10Days, backstageInLessThan5Days};
        final var app = new GildedRose(items);

        final var expectedQuality = 50;

        // When
        app.updateQuality();

        // Then
        assertEquals(backStageName, app.items[0].name);
        assertEquals(expectedQuality, app.items[0].quality);

        assertEquals(backStageName, app.items[1].name);
        assertEquals(expectedQuality, app.items[1].quality);

        assertEquals(backStageName, app.items[2].name);
        assertEquals(expectedQuality, app.items[2].quality);
    }
}
