package com.hathway.pocketgoals.presentation.ui.navigation_content

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.data.CategoryFlowStep
import com.hathway.pocketgoals.domain.ExpenseCategory
import com.hathway.pocketgoals.presentation.ui.components.category_components.AddEditCategoryForm
import com.hathway.pocketgoals.presentation.ui.components.category_components.CategorySelectionGrid
import com.hathway.pocketgoals.presentation.ui.components.category_components.DeleteCategoryConfirmation

@Composable
fun CategorySelectionContent(
    selectedCategory: ExpenseCategory?,
    onCategorySelected: (ExpenseCategory) -> Unit,
    onCategoryFinalized: (ExpenseCategory) -> Unit,
    currentStep: CategoryFlowStep,
    onStepChange: (CategoryFlowStep) -> Unit
) {
    var userCategories by remember { mutableStateOf(ExpenseCategory.defaultCategories) }

    Column(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = currentStep,
            modifier = Modifier.weight(1f).fillMaxSize(),
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "CategoryFlow"
        ) { step ->
            when (step) {
                is CategoryFlowStep.Selection -> {
                    CategorySelectionGrid(
                        categories = userCategories,
                        selectedCategory = selectedCategory,
                        onCategorySelected = onCategorySelected,
                        onCategoryLongClick = { onStepChange(CategoryFlowStep.Edit(it)) },
                        onAddNewCategory = { onStepChange(CategoryFlowStep.Add) },
                        modifier = Modifier.padding(16.dp)
                    )
                }
                is CategoryFlowStep.Add -> {
                    AddEditCategoryForm(
                        onSave = { newCat ->
                            userCategories = userCategories + newCat
                            onCategorySelected(newCat)
                            onStepChange(CategoryFlowStep.Selection)
                        }
                    )
                }
                is CategoryFlowStep.Edit -> {
                    AddEditCategoryForm(
                        initialCategory = step.category,
                        onSave = { updatedCat ->
                            userCategories = userCategories.map { if (it.name == step.category.name) updatedCat else it }
                            if (selectedCategory?.name == step.category.name) onCategorySelected(updatedCat)
                            onStepChange(CategoryFlowStep.Selection)
                        },
                        onDelete = {
                            onStepChange(CategoryFlowStep.Delete(step.category))
                        }
                    )
                }
                is CategoryFlowStep.Delete -> {
                    DeleteCategoryConfirmation(
                        category = step.category,
                        onConfirm = {
                            userCategories = userCategories.filter { it.name != step.category.name }
                            // If deleted was selected, we could clear it, but usually selection happens before edit
                            onStepChange(CategoryFlowStep.Selection)
                        },
                        onCancel = {
                            onStepChange(CategoryFlowStep.Edit(step.category))
                        }
                    )
                }
            }
        }

        if (currentStep is CategoryFlowStep.Selection && selectedCategory != null) {
            Box(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = { onCategoryFinalized(selectedCategory) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Done", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}