package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.model.FurnitureCategory;
import JavaAPI_DUC_Company1.repository.FurnitureCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FurnitureCategoryServiceTest {
//  @Mock: to create a fake object that has the same type as this repository.
//  Therefore, this fake object (_repo) does not communicate with the database.
    @Mock
    private FurnitureCategoryRepository _repo;

//  @InjectMocks : inject mocks into _service.
    @InjectMocks
    private FurnitureCategoryService _service;

    // getAll()

    @Test
    void getAll_shouldReturnAllFurnitureCategories() {
        // Arrange
        FurnitureCategory category1 = new FurnitureCategory();
        category1.setId(1);
        category1.setName("Chair");

        FurnitureCategory category2 = new FurnitureCategory();
        category2.setId(2);
        category2.setName("Table");

        List<FurnitureCategory> categories = List.of(category1, category2);
        //The following line means : when _repo.findAll() is called => return categories.
        when(_repo.findAll()).thenReturn(categories);

        // Act
        List<FurnitureCategory> result = _service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("Chair", result.get(0).getName());
        assertEquals("Table", result.get(1).getName());
// verify() is used to check that a method was called.
//  The following line means : _repo.findAll() was called exactly once.
        verify(_repo, times(1)).findAll();
    }

    // getById() - SUCCESS

    @Test
    void getById_shouldReturnFurnitureCategory_whenIdExists() {
        // Arrange
        Integer id = 1;

        FurnitureCategory category = new FurnitureCategory();
        category.setId(id);
        category.setName("Chair");

        when(_repo.findById(id)).thenReturn(Optional.of(category));

        // Act
        FurnitureCategory result = _service.getById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Chairrrrrr", result.getName());

        verify(_repo, times(1)).findById(id);
    }

    // getById() - NOT FOUND

    @Test
    void getById_shouldThrowException_whenIdDoesNotExist() {

        // Arrange
        Integer id = 1000;
        when(_repo.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> _service.getById(id)
        );

        assertEquals(
                "FurnitureCategory not found with id " + id,
                exception.getMessage()
        );

        verify(_repo, times(1)).findById(id);
    }

    // addUpdate() - ADD with null ID

    @Test
    void addUpdate_shouldCreateNewCategory_whenIdIsNull() {

        // Arrange
        FurnitureCategory model = new FurnitureCategory();
        model.setId(null);
        model.setName("Chair");
// savedCategory represents what I expect the repository/database to return after saving.
        FurnitureCategory savedCategory = new FurnitureCategory();
        savedCategory.setId(1);
        savedCategory.setName("Chair");
// The following line means :  Whenever _repo.save() is called with any FurnitureCategory,
//  don't actually go to the database. Return savedCategory instead.
        doReturn(savedCategory).when(_repo)
                .save(any(FurnitureCategory.class));

        // Act
        FurnitureCategory result = _service.addUpdate(model);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Chair", result.getName());

        // Capture the object sent to repository.save()
        ArgumentCaptor<FurnitureCategory> captor =
                ArgumentCaptor.forClass(FurnitureCategory.class);

        verify(_repo, times(1)).save(captor.capture());

        FurnitureCategory savedObject = captor.getValue();

        assertNotNull(savedObject);
        assertEquals("Chair", savedObject.getName());

        // New object should not have an ID assigned by the service.
        // The database/repository would normally generate the ID.
        assertTrue(savedObject.getId() == null || savedObject.getId() == 0);

        // ADD operation should NOT call findById()
        verify(_repo, never()).findById(any());
    }

    // addUpdate() - ADD with ID = 0

    @Test
    void addUpdate_shouldCreateNewCategory_whenIdIsZero() {
        // Arrange
        FurnitureCategory model = new FurnitureCategory();
        model.setId(0);
        model.setName("Table");

        FurnitureCategory savedCategory = new FurnitureCategory();
        savedCategory.setId(5);
        savedCategory.setName("Table");

        when(_repo.save(any(FurnitureCategory.class))).thenReturn(savedCategory);

        // Act
        FurnitureCategory result = _service.addUpdate(model);

        // Assert
        assertNotNull(result);
        assertEquals(5, result.getId());
        assertEquals("Table", result.getName());

        verify(_repo, times(1)).save(any(FurnitureCategory.class));

        // Because this is ADD, findById() should not be called.
        verify(_repo, never()).findById(any());
    }

    // addUpdate() - UPDATE

    @Test
    void addUpdate_shouldUpdateExistingCategory_whenIdExists() {

        // Arrange
        Integer id = 1;

        FurnitureCategory model = new FurnitureCategory();
        model.setId(id);
        model.setName("Office Chair");

        FurnitureCategory existingCategory = new FurnitureCategory();
        existingCategory.setId(id);
        existingCategory.setName("Chair");

        FurnitureCategory savedCategory = new FurnitureCategory();
        savedCategory.setId(id);
        savedCategory.setName("Office Chair");

        when(_repo.findById(id)).thenReturn(Optional.of(existingCategory));
        when(_repo.save(existingCategory)).thenReturn(savedCategory);

        // Act
        FurnitureCategory result = _service.addUpdate(model);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Office Chair", result.getName());

        // Verify that the existing entity was actually changed.
        assertEquals("Office Chair", existingCategory.getName());

        verify(_repo, times(1)).findById(id);
        verify(_repo, times(1)).save(existingCategory);
    }

    // addUpdate() - UPDATE but ID does not exist

    @Test
    void addUpdate_shouldThrowException_whenUpdatingNonExistingCategory() {

        // Arrange
        Integer id = 100;

        FurnitureCategory model = new FurnitureCategory();
        model.setId(id);
        model.setName("Unknown Furniture");

        when(_repo.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> _service.addUpdate(model)
        );

        assertEquals(
                "FurnitureCategory not found with id " + id,
                exception.getMessage()
        );

        verify(_repo, times(1)).findById(id);

        // Because the category was not found,
        // save() must NOT be called.
        verify(_repo, never()).save(any(FurnitureCategory.class));
    }

    // delete()

    @Test
    void delete_shouldDeleteFurnitureCategory() {

        // Arrange
        Integer id = 1;
//public void delete(Integer id){...} : void does not return anything.
//As a result, in the following line, there is "doNothing()"
//The following line is optional.
        doNothing().when(_repo).deleteById(id);

        // Act
        _service.delete(id);

        // Assert
        verify(_repo, times(1)).deleteById(id);
    }
}
