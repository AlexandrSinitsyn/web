package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {

    private void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("state", getFromSession(request));
    }

    @SuppressWarnings("unchecked")
    private <T> T objOrDefault(Object obj, T init) {
        return obj == null ? init : (T) obj;
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        final State state = getFromSession(request);

        if (state.getPhase() == Phase.RUNNING) {
            String[] pos = getPosition(request).split("");
            try {
                if (pos.length < 2) throw new NumberFormatException();

                final int i = Integer.parseInt(pos[0]);
                final int j = Integer.parseInt(pos[1]);

                state.setCell(i, j);

                if (checkInRow(i, j, state, state.countInRow)) {
                    state.setPhase(state.crossesMove ? Phase.WON_X : Phase.WON_O);
                } else if (state.isAllFilled()) {
                    state.setPhase(Phase.DRAW);
                }

                state.step();
                pushToSession(request, state);
            } catch (State.StateException ignored) {
                // do nothing
            } catch (NumberFormatException ignored) {
                // do nothing
            }
        }

        action(request, view);
    }

    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        pushToSession(request, new State());

        action(request, view);
    }


    private State getFromSession(HttpServletRequest request) {
        return objOrDefault(request.getSession().getAttribute("state"), new State());
    }

    private void pushToSession(HttpServletRequest request, State state) {
        request.getSession().setAttribute("state", state);
    }


    private String getPosition(HttpServletRequest request) {
        final String prefix = "cell_";

        for (Map.Entry<String, String[]> param : request.getParameterMap().entrySet()) {
            if (param.getKey().startsWith(prefix)) {
                return param.getKey().substring(prefix.length());
            }
        }

        return "";
    }


    private boolean checkInRow(int i, int j, State state, int countInRow) {
        int max = 0;

        int[][] dirs = new int[][]{
                new int[]{1, 0}, new int[]{0, 1}, new int[]{-1, 1}, new int[]{1, 1}
        };

        for (int[] d : dirs) {
            int cnt = checkInRow(i, j, d[0], d[1], state);

            if (max < cnt) {
                max = cnt;
            }
        }

        return max >= countInRow;
    }

    private int checkInRow(int i, int j, int dy, int dx, State state) {
        return checkInRow(i, j, dy, dx, state, 0) + checkInRow(i, j, -dy, -dx, state, 0) - 1;
    }

    private int checkInRow(int i, int j, int dy, int dx, State state, int count) {
        if (i >= 0 && i < state.size && j >= 0 && j < state.size &&
                state.isCellNotEmpty(i, j) && state.isCellX(i, j) == state.crossesMove) {
            return checkInRow(i + dy, j + dx, dy, dx, state, count + 1);
        }

        return count;
    }


    public static final class State {
        private final int size = 3;
        private final int countInRow = 3;
        private final Cell[][] cells;

        private boolean crossesMove;
        private Phase phase;
        private int emptyCells;


        public State() {
            crossesMove = true;
            cells = new Cell[size][size];

            emptyCells = size * size;
            phase = Phase.RUNNING;
        }


        private final static class StateException extends RuntimeException {}


        private void setCell(int i, int j) throws StateException {
            if (i < 0 || i >= size || j < 0 || j >= size) {
                throw new StateException();
            }

            if (isCellNotEmpty(i, j)) {
                throw new StateException();
            }

            cells[i][j] = crossesMove ? Cell.X : Cell.O;

            emptyCells--;
        }

        private void step() {
            crossesMove = !crossesMove;
        }

        public void setPhase(Phase phase) {
            this.phase = phase;
        }


        public int getSize() {
            return size;
        }

        public Cell[][] getCells() {
            return cells;
        }

        public Cell getCell(int i, int j) {
            if (i < 0 || i >= size || j < 0 || j >= size) {
                throw new IndexOutOfBoundsException();
            }

            return cells[i][j];
        }

        public boolean isCrossesMove() {
            return crossesMove;
        }

        public Phase getPhase() {
            return phase;
        }


        private boolean isAllFilled() {
            return emptyCells == 0;
        }

        private boolean isCellX(int i, int j) {
            return getCell(i, j) == Cell.X;
        }

        private boolean isCellNotEmpty(int i, int j) {
            return getCell(i, j) != null;
        }
    }

    public enum Cell {
        X, O
    }

    public enum Phase {
        RUNNING, WON_X, WON_O, DRAW
    }
}
