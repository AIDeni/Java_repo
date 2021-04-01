class MFU {

    Object printLock = new Object();
    Object scanLock = new Object();

    public void print(String doc, int pages) {
        synchronized (printLock) {
            System.out.println("Задание на печать документа " + doc +" отправлено");
            try {
                Thread.sleep(200 * pages);
                System.out.println("Идет печать документа " + doc);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Печать документа " + doc + " окончена!");
            System.out.println();
        }

    }

    public void scan(String doc, int pages, int source) {
        synchronized (scanLock) {
            System.out.println("Начало сканирования документа " + doc);
            switch (source) {
                case 1 :
                    try {
                        Thread.sleep(200 * pages);
                        System.out.println("Идет сканирование документа " + doc);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2 :
                    synchronized(printLock) {
                        System.out.println("Копирование документа " + doc + " начато");
                        try {
                            Thread.sleep(200 * pages);
                            System.out.println("Идет копирование документа " + doc);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Печать копий документа " + doc + " завершена");
                        break;
                    }
            }
            System.out.println("Сканирование документа " + doc + " окончено!");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        MFU mfu = new MFU();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mfu.print("Лекция №7.pdf", 5);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mfu.print("Домашнее задание.pdf", 10);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mfu.scan("Домашнее задание.pdf", 10, 1);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mfu.scan("Справочник.pdf", 7, 2);
            }
        }).start();

    }

}