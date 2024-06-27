package com.codex.EcommersCodex.services;

import com.codex.EcommersCodex.models.ProdWish;
import com.codex.EcommersCodex.models.Producto;
import com.codex.EcommersCodex.models.Usuario;
import com.codex.EcommersCodex.repository.WishlistRepository;
import com.codex.EcommersCodex.repository.ProductoRepository;
import com.codex.EcommersCodex.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<Map<String, Object>> getWishlistByUserId(Long userId) {
        List<ProdWish> wishlist = wishlistRepository.findByUsuarioId(userId);
        List<Map<String, Object>> wishlistWithDetails = new ArrayList<>();

        for (ProdWish item : wishlist) {
            Map<String, Object> itemDetails = new HashMap<>();
            itemDetails.put("id", item.getId());
            itemDetails.put("idProd", item.getIdProd());

            // Aquí debes obtener los detalles del producto utilizando el idProd
            Producto producto = productoRepository.findById(item.getIdProd()).orElse(null);

            if (producto != null) {
                itemDetails.put("product", producto);
            }

            wishlistWithDetails.add(itemDetails);
        }

        return wishlistWithDetails;
    }

    public boolean addToWishlist(Long userId, Long productId) {
        List<ProdWish> wishlist = wishlistRepository.findByUsuarioId(userId);
        boolean exists = wishlist.stream().anyMatch(item -> item.getIdProd().equals(productId));
        if (exists) {
            return false;
        } else {
            ProdWish newWish = new ProdWish();
            Usuario usuario = usuarioRepository.findById(userId).orElse(null);
            if (usuario != null) {
                newWish.setUsuario(usuario);
                newWish.setIdProd(productId);
                wishlistRepository.save(newWish);
                return true;
            }
            return false;
        }
    }
}
