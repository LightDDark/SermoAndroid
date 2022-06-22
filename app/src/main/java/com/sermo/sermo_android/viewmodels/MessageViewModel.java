package com.sermo.sermo_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sermo.sermo_android.IO.OutMessage;
import com.sermo.sermo_android.enteties.Contact;
import com.sermo.sermo_android.enteties.Message;
import com.sermo.sermo_android.repositories.MessageRepository;

import java.util.ArrayList;
import java.util.List;

public class MessageViewModel extends ViewModel {
    private MessageRepository messageRepository;
    private LiveData<List<Message>> messages;

    public MessageViewModel(String contactId) {
        this.messageRepository = new MessageRepository(contactId);
        this.messages = messageRepository.getAll();
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public void add(String content) {
        messageRepository.add(new OutMessage(content));
    }

    public void reload() {
        messageRepository.reload();
    }
}
