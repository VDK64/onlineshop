package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.config.FileConfig;
import net.thumtack.onlineshop.dto.Response.ResponseSettingsDto;
import net.thumtack.onlineshop.services.interfaces.SettingsService;
import org.springframework.stereotype.Service;

@Service
public class SettingsServiceImpl implements SettingsService {
    @Override
    public ResponseSettingsDto servSettings() {
        return new ResponseSettingsDto(FileConfig.maxNameLength,FileConfig.minPasswordLength);
    }
}
