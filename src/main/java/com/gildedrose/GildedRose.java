package com.gildedrose;

import java.util.Arrays;

import static com.gildedrose.ItemCategory.getCategory;

class GildedRose {
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
    private static final int QUALITY_MAX = 50;
    private static final int QUALITY_MIN = 0;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        Arrays.stream(items)
                .forEach((item) -> newProcessItem(item));
    }

    private void processItem(Item item) {
        if (!item.name.equals(AGED_BRIE)
                && !item.name.equals(BACKSTAGE)) {
            if (item.quality > 0) {
                if (!item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
                    item.quality = item.quality - 1;
                }
            }
        } else {
            if (item.quality < QUALITY_MAX) {
                item.quality = item.quality + 1;

                if (item.name.equals(BACKSTAGE)) {
                    if (item.sellIn < 11) {
                        if (item.quality < QUALITY_MAX) {
                            item.quality = item.quality + 1;
                        }
                    }

                    if (item.sellIn < 6) {
                        if (item.quality < QUALITY_MAX) {
                            item.quality = item.quality + 1;
                        }
                    }
                }
            }
        }

        if (!item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
            item.sellIn = item.sellIn - 1;
        }

        if (item.sellIn < 0) {
            if (!item.name.equals(AGED_BRIE)) {
                if (!item.name.equals(BACKSTAGE)) {
                    if (item.quality > 0) {
                        if (!item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
                            item.quality = item.quality - 1;
                        }
                    }
                } else {
                    item.quality = item.quality - item.quality;
                }
            } else {
                if (item.quality < QUALITY_MAX) {
                    item.quality = item.quality + 1;
                }
            }
        }
    }

    private void newProcessItem(Item item) {
        switch (getCategory(item)) {
            case AGED_BRIE:
                processBrie(item);
                break;
            case BACKSTAGE:
            case SULFURAS_HAND_OF_RAGNAROS:
                processItem(item);
                break;
            default:
                processOrdinaryItem(item);
        }
    }

    private void processOrdinaryItem(Item item) {
        var decreaseFactor = item.sellIn > 0 ? 1 : 2;
        item.quality = item.quality > QUALITY_MIN ? item.quality - decreaseFactor : item.quality;
        item.sellIn--;
    }

    private void processBrie(Item item) {
        var increaseFactor = item.sellIn > 0 ? 1 : 2;
        item.quality = item.quality < QUALITY_MAX ? item.quality + increaseFactor : item.quality;
        item.sellIn--;
    }
}
