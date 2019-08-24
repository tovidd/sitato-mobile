<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateTransaksiPenjualanJasaServiceTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('transaksi_penjualan_jasa_service', function (Blueprint $table) {
            $table->increments('id_transaksi_penjualan_jasa_service');
            $table->integer('id_transaksi')->unsigned();
            $table->integer('id_montir')->unsigned();
            $table->integer('id_jasa_service')->unsigned();
            $table->integer('id_kendaraan')->unsigned(); 
            $table->double('jumlah_transaksi_penjualan_jasa_service');
            $table->double('subtotal_transaksi_penjualan_jasa_service');
            $table->timestamps();
            $table->softDeletes();
            //foreign
            $table->foreign('id_transaksi')->references('id_transaksi')->on('transaksi');
            $table->foreign('id_montir')->references('id_pegawai')->on('pegawai');
            $table->foreign('id_jasa_service')->references('id_jasa_service')->on('jasa_service');
            $table->foreign('id_kendaraan')->references('id_kendaraan')->on('kendaraan');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('transaksi_penjualan_jasa_service');
    }
}
