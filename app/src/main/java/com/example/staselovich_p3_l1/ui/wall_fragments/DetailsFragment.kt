package com.example.staselovich_p3_l1.ui.wall_fragments


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.WallpaperManager
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.staselovich_p3_l1.R
import com.example.staselovich_p3_l1.dataBase.EntyImage
import com.example.staselovich_p3_l1.databinding.FragmentDetailsBinding
import com.example.staselovich_p3_l1.tools.showAlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var isImageFitToScreen: Boolean = false
    lateinit var viewModel: DetailsViewModel
    private val args by navArgs<DetailsFragmentArgs>()
    private var dialog: AlertDialog? = null

    @SuppressLint("SetTextI18n", "ResourceType")
    @DelicateCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.INVISIBLE
        _binding = FragmentDetailsBinding.bind(view)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        binding.detailsViewModel = viewModel
        viewModel.saveArgs= args.photo
        fullScreen()
        loadingImage()
        twiterVisable()
        binding.viewShadow.animate().alpha(0.0f)
        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
        // motion layout
        viewModel.loadingEnded.observe(viewLifecycleOwner, {
            dialog?.dismiss()
        })
        binding.imageBack.setOnClickListener {
            showVisableInfo()
            animationFallInfo()
            binding.info.constInfo.visibility = View.GONE
        }
        binding.buttonShare.setOnClickListener {
            shareSuccess()
        }
        binding.viewShadow.setOnClickListener {
            binding.menuTenu.linerPop.visibility = View.GONE
            animationFallMenu()
            binding.viewShadow.visibility = View.GONE
            binding.viewShadow.animate().alpha(0.0f)
            showVisableInfo()
            showFullScreenViseble()
        }
        binding.buttonTune.setOnClickListener {
            binding.imageBack.visibility = View.GONE
            binding.viewShadow.visibility = View.VISIBLE
            binding.viewShadow.animate().alpha(1.0f)
            binding.imageFullScreen.visibility = View.INVISIBLE
            showPopMenu()
            showFullScreenInviseble()
        }
        binding.buttonInfo.setOnClickListener {
            showInfo()
            binding.imageFullScreen.visibility = View.INVISIBLE
            showInvisebleInfo()
        }
    }
    private fun shareSuccess() {
        startActivity(viewModel.share())
    }
    @SuppressLint("ResourceType")
    fun fullScreen() {
        binding.imageFullScreen.setOnClickListener {
            if(isImageFitToScreen){
                isImageFitToScreen = false
                binding.imageView.adjustViewBounds = true
                showFullScreenViseble()
                animationToolBarRight()
                binding.imageFullScreen.setImageResource(R.drawable.ic_baseline_fullscreen_24)
            } else {
                isImageFitToScreen = true
                binding.imageView.layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT
                )
                binding.imageFullScreen.setImageResource(R.drawable.ic_baseline_fullscreen_exit_24)
                binding.imageView.scaleType = ImageView.ScaleType.FIT_XY
                showFullScreenInviseble()
                animationToolBarLeft()
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "SimpleDateFormat", "InflateParams", "ResourceType", "CutPasteId")
    fun showInfo() {
        binding.info.constInfo.visibility = View.VISIBLE
        animationUpInfo()
        binding.apply {
            context?.let {
                Glide.with(it)
                    .load(viewModel.saveArgs?.user?.profile_image?.large)
                    .centerCrop()
                    .into(binding.info.imageButton)
            }
        }
        binding.info.buttonPortfolio.setOnClickListener {
            if(args.photo.user.portfolio_url == null){
                Toast.makeText(context, resources.getString(R.string.NoLink), Toast.LENGTH_SHORT).show()
            }else {
                getLink(args.photo.user.portfolio_url.toString())
            }
        }
        binding.info.imageInstogram.setOnClickListener {
            if(args.photo.user.portfolio_url == null){
                Toast.makeText(context, resources.getString(R.string.NoLink), Toast.LENGTH_SHORT).show()
            }else {
                getLink("https://twitter.com/${args.photo.user.instagram_username}/")
            }
        }
        binding.info.imageVector.setOnClickListener {
            getLink("https://twitter.com/${args.photo.user.twitter_name}/")
        }
    }
@RequiresApi(Build.VERSION_CODES.O)
@DelicateCoroutinesApi
@SuppressLint("InflateParams")
fun showPopMenu() {
    binding.menuTenu.linerPop.visibility = View.VISIBLE
    animationUpMenu()
    binding.menuTenu.setHomeScreenWallpaper.setOnClickListener {
        dialog = requireContext().showAlertDialog()
        lifecycleScope.launch(Dispatchers.IO) {
            setHomeScreenWallpaper()
        }
    }
binding.menuTenu.setLockScreenWallpaper.setOnClickListener {
    dialog = requireContext().showAlertDialog()
    lifecycleScope.launch(Dispatchers.IO) {
        setLockScreenWallpaper()
    }
}
    binding.menuTenu.addSelect.setOnClickListener {
        dialog = requireContext().showAlertDialog()
        viewModel.saveArgs?.let { it1 ->
            EntyImage(0,
                it1.id, viewModel.saveArgs!!.urls.full)
        }?.let { it2 -> viewModel.addImage(it2) }
    }
}
    private fun setHomeScreenWallpaper() {
        val imgBitmap = (binding.imageView.drawable as BitmapDrawable).bitmap
        val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(requireContext())
        wallpaperManager.setBitmap(imgBitmap)
        viewModel.loadingEnded.postValue(true)
    }
    private fun setLockScreenWallpaper() {
        val imgBitmap = (binding.imageView.drawable as BitmapDrawable).bitmap
        val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(requireContext())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            wallpaperManager.setBitmap(imgBitmap, null, true, WallpaperManager.FLAG_LOCK)
        }
        viewModel.loadingEnded.postValue(true)
    }
    private fun getLink(link: String) {
        val action = DetailsFragmentDirections.actionDetailsFragmentToWebViewFragment2(link)
        findNavController().navigate(action)
    }
    private fun loadingImage() {
        binding.apply {
            val photo = viewModel.saveArgs
            Glide.with(this@DetailsFragment) .load(photo?.urls?.full).centerCrop()
                .error(R.drawable.ic_baseline_error_24)
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }
                }).into(imageView)
        }
    }
    private fun twiterVisable(){
        if(viewModel.saveArgs?.user?.portfolio_url == null){
            binding.info.imageVector.visibility = View.GONE
        }
    }
    private fun showVisableInfo() {
        binding.imageBack.visibility = View.GONE
        binding.imageFullScreen.visibility = View.VISIBLE
        binding.buttonBack.visibility = View.VISIBLE
        binding.toolbar.visibility = View.VISIBLE
    }
    private fun showInvisebleInfo() {
        binding.imageBack.visibility = View.VISIBLE
        binding.buttonBack.visibility = View.INVISIBLE
        binding.toolbar.visibility = View.INVISIBLE
    }
    private fun showFullScreenInviseble() {
        binding.buttonBack.visibility = View.INVISIBLE
        binding.linear.visibility = View.INVISIBLE
        binding.toolbar.visibility = View.INVISIBLE
    }
    private fun showFullScreenViseble() {
        binding.buttonBack.visibility = View.VISIBLE
        binding.linear.visibility = View.VISIBLE
        binding.toolbar.visibility = View.VISIBLE
        binding.imageFullScreen.visibility = View.VISIBLE
    }
    private fun animationUpInfo() {
        val upAnimation = AnimationUtils.loadAnimation(context, R.anim.up_pop_menu)
        binding.info.constInfo.startAnimation(upAnimation)
    }
    private fun animationFallInfo() {
        val fallinAnimation = AnimationUtils.loadAnimation(context, R.anim.falling)
        binding.info.constInfo.startAnimation(fallinAnimation)
    }
    private fun animationUpMenu() {
        val upAnimation = AnimationUtils.loadAnimation(context, R.anim.up_pop_menu)
        binding.menuTenu.linerPop.startAnimation(upAnimation)
    }
    private fun animationFallMenu() {
        val fallinAnimation = AnimationUtils.loadAnimation(context, R.anim.falling)
        binding.menuTenu.linerPop.startAnimation(fallinAnimation)
    }
    private fun animationToolBarLeft() {
        val upAnimation = AnimationUtils.loadAnimation(context, R.anim.left_anim)
        binding.toolbar.startAnimation(upAnimation)
        val upAnimation2 = AnimationUtils.loadAnimation(context, R.anim.left_anim)
        binding.linear.startAnimation(upAnimation2)
    }
    private fun animationToolBarRight() {
        val fallinAnimation = AnimationUtils.loadAnimation(context, R.anim.right_anim)
        binding.toolbar.startAnimation(fallinAnimation)
        val fallinAnimation2 = AnimationUtils.loadAnimation(context, R.anim.right_anim)
        binding.linear.startAnimation(fallinAnimation2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE
    }
}