package com.exostar.userprofile.batch.listener;

import java.util.List;
import org.springframework.batch.core.ItemWriteListener;
import com.exostar.userprofile.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRecordWriteListener implements ItemWriteListener<UserEntity> {
    @Override
    public void beforeWrite(List<? extends UserEntity> list) {
        log.info("writing {} user entity", list.size());
    }

    @Override
    public void afterWrite(List<? extends UserEntity> list) {
        log.info("completed writing {} user entity", list.size());
    }

    @Override
    public void onWriteError(Exception e, List<? extends UserEntity> list) {
        log.error("writing failed size {}", list.size(), e);
    }
}
