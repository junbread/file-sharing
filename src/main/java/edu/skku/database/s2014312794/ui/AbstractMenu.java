package edu.skku.database.s2014312794.ui;

import edu.skku.database.s2014312794.util.ConsoleUtil;

public abstract class AbstractMenu implements Menu {

    protected boolean isLoop() {
        return true;
    }

    protected void doHeader() {
        System.out.printf("\n:::: %s ", name());
        for (int i = 6 + name().length(); i < 80; i++)
            System.out.print(":");
        System.out.println("\n");
    }

    protected abstract void doBody();

    @Override
    public void run() {
        boolean isLoop = isLoop();

        do try {
            doHeader();
            doBody();
        } catch (Exception e) {
            if (!(e instanceof ConsoleUtil.BackSelectedException))
                e.printStackTrace();
            return;
        } while (isLoop);
    }
}
