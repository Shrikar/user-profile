package com.exostar.userprofile.batch.listener;

import javax.batch.api.listener.StepListener;
import org.springframework.batch.core.SkipListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRecordSkipListener implements SkipListener {
    @Override
    public void onSkipInRead(Throwable throwable) {
        log.error("Skipped reading", throwable);
    }

    @Override
    public void onSkipInWrite(Object o, Throwable throwable) {
        log.error("Skipped writing {} class {}", o, o.getClass(), throwable);
    }

    @Override
    public void onSkipInProcess(Object o, Throwable throwable) {
        log.error("Skipped processing {} class {}", o, o.getClass(), throwable);
    }
}
