package pl.wat.wcy.panek.simulateddevices.application;

import lombok.SneakyThrows;

import java.util.List;

abstract class DataService {

    protected final MessagePublisher messagePublisher;

    public DataService(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    public void publish(String userId) {
        while (true) {
            data(userId).forEach(val -> {
                sleep(val);
                messagePublisher.send(message(val, userId));
            });
        }
    }

    protected abstract List<FileEntry> data(String userId);
    protected abstract Message message(FileEntry val, String userId);


    @SneakyThrows
    private void sleep(FileEntry val) {
        Thread.sleep((long) (val.getEnd() * 1000 - val.getStart() * 1000));
    }
}
