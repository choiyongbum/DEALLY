package com.example.product.service;

import com.example.product.domain.Item;
import com.example.product.repository.ItemRepository;
import com.example.product.usecase.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemCommandService implements ItemSaveUseCase, ItemSelectAllUseCase, ItemSelectOneUseCase, ItemUpdateUseCase, ItemDeleteUseCase {
    private final ItemRepository itemRepository;

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }
    
    @Override
    public Page<Item> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    @Override
    public Page<Item> searchByItemName(String searchKeyword, Pageable pageable) {
        return itemRepository.findByItemNameContaining(searchKeyword, pageable);
    }


    @Override
    public Item findById(Long id) {
        return itemRepository.findProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + id));
    }

    @Override
    public Item findByUserId(Long userId) {
        return itemRepository.findProductByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + userId));
    }

    @Transactional
    @Override
    public Item update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.updateFields(
                updateParam.getItemName(),
                updateParam.getPrice(),
                updateParam.getQuantity()
        );
        
        return itemRepository.save(findItem);
    }

    @Override
    public void delete(Long itemId) {
        Item findItem = findById(itemId);
        itemRepository.delete(findItem);
        
    }
    
}
