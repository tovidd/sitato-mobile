<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateTransaksiPenjualanSparepartTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('transaksi_penjualan_sparepart', function (Blueprint $table) {
            $table->increments('id_transaksi_penjualan_sparepart');
            $table->integer('id_transaksi')->unsigned();
            $table->integer('id_sparepart')->unsigned();
            $table->double('jumlah_transaksi_penjualan_sparepart');
            $table->double('subtotal_transaksi_penjualan_sparepart');
            $table->timestamps();
            $table->softDeletes();
            //foreign
            $table->foreign('id_transaksi')->references('id_transaksi')->on('transaksi');
            $table->foreign('id_sparepart')->references('id_sparepart')->on('sparepart');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('transaksi_penjualan_sparepart');
    }
}
