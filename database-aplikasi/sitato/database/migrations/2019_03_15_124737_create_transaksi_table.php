<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateTransaksiTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('transaksi', function (Blueprint $table) {
            $table->increments('id_transaksi');
            $table->integer('id_pelanggan')->unsigned();
            $table->integer('id_cs')->unsigned();
            $table->integer('id_kasir')->unsigned();
            $table->integer('id_status_pengerjaan')->unsigned();
            $table->double('total_transaksi');
            $table->double('diskon_transaksi');
            $table->double('jumlah_uang_pembayaran_transaksi');
            $table->timestamps();
            $table->softDeletes();
            //foreign
            $table->foreign('id_pelanggan')->references('id_pelanggan')->on('pelanggan');
            $table->foreign('id_cs')->references('id_pegawai')->on('pegawai');
            $table->foreign('id_kasir')->references('id_pegawai')->on('pegawai');
            $table->foreign('id_status_pengerjaan')->references('id_status_pengerjaan')->on('status_pengerjaan');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('transaksi');
    }
}
