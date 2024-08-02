package com.example.todolist.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.todolist.R
import com.example.todolist.databinding.FragmentSettingsBinding
import com.example.todolist.presentation.viewModels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
        observeViewModel()
    }

    private fun listeners() {
        binding.completedColorRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val color = getColorId(checkedId)
            viewModel.setCompletedColor(color)
        }
        binding.notCompletedColorRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val color = getColorId(checkedId)
            viewModel.setNotCompletedColor(color)
        }
    }
    private fun observeViewModel(){
        lifecycleScope.launch {
            viewModel.completedColor.collect{
                binding.completedColorRadioGroup.check(getRadioButtonId(it))
            }
        }
        lifecycleScope.launch {
            viewModel.notCompletedColor.collect{
                binding.notCompletedColorRadioGroup.check(getRadioButtonId(it))
            }
        }
    }
    private fun getColorId(checkedId: Int): Int {
        return when (checkedId) {
            R.id.greenRadioButton -> R.color.green
            R.id.blueRadioButton -> R.color.blue
            R.id.yellowRadioButton -> R.color.yellow
            R.id.brownRadioButton -> R.color.brown
            R.id.pinkRadioButton -> R.color.pink
            R.id.violetRadioButton -> R.color.violet
            else -> 1
        }
    }
    private fun getRadioButtonId(color: Int): Int {
        return when (color) {
            R.color.green -> R.id.greenRadioButton
            R.color.blue -> R.id.blueRadioButton
            R.color.yellow -> R.id.yellowRadioButton
            R.color.brown -> R.id.brownRadioButton
            R.color.pink -> R.id.pinkRadioButton
            R.color.violet -> R.id.violetRadioButton
            else -> 1
        }
    }
}