<?php

namespace sitato\Http\Controllers;

use sitato\detilPengadaanSparepart;
use Illuminate\Http\Request;

class detilPengadaanSparepartController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $data= detilPengadaanSparepart::all();
        return response()->json($data, 200);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $data= new detilPengadaanSparepart;
        $data->id_pengadaan_sparepart= $request->id_pengadaan_sparepart;
        $data->id_sparepart= $request->id_sparepart;
        $data->satuan_detil_pengadaan_sparepart= $request->satuan_detil_pengadaan_sparepart;
        $data->jumlah_beli_detil_pengadaan_sparepart= $request->jumlah_beli_detil_pengadaan_sparepart;
        $data->subtotal_detil_pengadaan_sparepart= $request->subtotal_detil_pengadaan_sparepart;
        $success= $data->save();
        if(!$success)
        {
            return response()->json('Error saving', 500);
        }
        else
        {
            return response()->json('Success', 200);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  \sitato\detilPengadaanSparepart  $detilPengadaanSparepart
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $data= detilPengadaanSparepart::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }
        else
        {
            return response()->json($data, 200);
        }
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \sitato\detilPengadaanSparepart  $detilPengadaanSparepart
     * @return \Illuminate\Http\Response
     */
    public function edit(detilPengadaanSparepart $detilPengadaanSparepart)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \sitato\detilPengadaanSparepart  $detilPengadaanSparepart
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $data= detilPengadaanSparepart::find($id);
        if(!is_null($request->id_pengadaan_sparepart))
        {
            $data->id_pengadaan_sparepart= $request->id_pengadaan_sparepart;
        }
        if(!is_null($request->id_sparepart))
        {
            $data->id_sparepart= $request->id_sparepart;
        }
        if(!is_null($request->satuan_detil_pengadaan_sparepart))
        {
            $data->satuan_detil_pengadaan_sparepart= $request->satuan_detil_pengadaan_sparepart;
        }
        if(!is_null($request->jumlah_beli_detil_pengadaan_sparepart))
        {
            $data->jumlah_beli_detil_pengadaan_sparepart= $request->jumlah_beli_detil_pengadaan_sparepart;
        }
        if(!is_null($request->subtotal_detil_pengadaan_sparepart))
        {
            $data->subtotal_detil_pengadaan_sparepart= $request->subtotal_detil_pengadaan_sparepart;
        }

        $success= $data->save();
        if(!$success)
        {
            return response()->json('Error updating', 500);
        }
        else
        {
            return response()->json('Success updating', 200);
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \sitato\detilPengadaanSparepart  $detilPengadaanSparepart
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $data= detilPengadaanSparepart::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }

        $success= $data->delete();
        if(!$success)
        {
            return response()->json('Error deleting', 500);
        }
        else
        {
            return response()->json('Success deleting', 200);
        }
    }
}
