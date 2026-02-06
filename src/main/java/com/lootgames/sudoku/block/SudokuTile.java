package com.lootgames.sudoku.block;

import com.lootgames.sudoku.sudoku.GameSudoku;

import ru.timeconqueror.lootgames.api.block.tile.BoardGameMasterTile;

public class SudokuTile extends BoardGameMasterTile<GameSudoku> {

    public SudokuTile() {
        super(new GameSudoku());
    }

    /**
     * Called by ActivatorBlock when setting up board, passing in the config snapshot.
     */
    public void init() {}
}
