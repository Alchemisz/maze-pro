package com.promaze.solvers;

import com.promaze.Maze;

import java.util.List;

public interface Solver {
    public List<Maze> solve(Maze maze);
}
